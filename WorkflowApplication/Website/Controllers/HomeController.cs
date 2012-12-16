using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading;
using System.Threading.Tasks;
using System.Web.Mvc;
using System.Web.Security;
using Website.Infrastructure;
using Website.Models;
using Workflows;

namespace Website.Controllers
{
    [Authorize]
    public class HomeController : Controller
    {
        private IInvoker invoker;

        public HomeController(IInvoker invoker)
        {
            this.invoker = invoker;
        }

        public ActionResult Index()
        {
            return View();
        }

        #region Sentiment Query
        public PartialViewResult SentimentQuery()
        {
            return PartialView(new DateInterval { DateFrom = DateTime.Now.Subtract(TimeSpan.FromDays(14)), DateTo = DateTime.Now });
        }

        [HttpPost]
        public ActionResult SentimentQueryLaunch(DateInterval interval)
        {
            if (Session["Query"] != null)
                return Json(new { Error = "There is already a sentiment query in progress." });
            var mq = new Queue<String>();
            var ev = new AutoResetEvent(false);
            var context = new InvokerContext(
                new QuerySentiment(),
                new { interval.DateFrom, interval.DateTo, CompanyName = Session["CompanyName"] },
                message =>
                {
                    mq.Enqueue(message);
                    ev.Set();
                },
                track => { });
            Session["Query"] = new QueryContext(context, mq, ev);
            Task.Run(() =>
                {
                    invoker.Invoke(context);
                    ev.Set();
                }).ContinueWith(t =>
                {
                    mq.Enqueue("Error: " + t.Exception.GetBaseException().ToString());
                    ev.Set();
                }, TaskContinuationOptions.OnlyOnFaulted);
            return SentimentQueryProgress();
        }

        [HttpPost]
        public JsonResult SentimentQueryProgress()
        {
            var query = Session["Query"] as QueryContext;
            if (query == null)
                return Json(new { Error = "There is no sentiment query in progress." });
            if (!query.ActivityEvent.WaitOne(15000))
                return Json(new { Error = "The query timed out." });
            var messages = new List<String>();
            while (query.MessageQueue.Count > 0) messages.Add(query.MessageQueue.Dequeue());
            return Json(
                new
                {
                    query.InvokerContext.IsFinished,
                    Messages = messages
                });
        }

        [HttpPost]
        public PartialViewResult SentimentQueryResult()
        {
            var query = Session["Query"] as QueryContext;
            Session.Remove("Query");
            return PartialView(query.InvokerContext);
        }
        #endregion

        #region Billing
        public PartialViewResult Billing()
        {
            var context = invoker.Invoke(new InvokerContext(
                new GetCurrentBill(),
                new { CompanyName = Session["CompanyName"] }
            ));
            ViewBag.Messages = context.Messages;
            return PartialView(new BillingInformation { CurrentBill = context.Results.CurrentBill });
        }

        [HttpPost]
        public JsonResult PayBill(BillingInformation billInfo)
        {
            var context = invoker.Invoke(new InvokerContext(
                new PayCurrentBill(),
                new { CompanyName = Session["CompanyName"], billInfo.UserAccountData }));

            if (!context.Results.PaymentSucceeded)
                return ControllerExtensions.CreateModelErrors(this, context.Messages.ToArray());

            return Json(new { Success = true, context.Messages });
        }
        #endregion

        public PartialViewResult Account()
        {
            var context = invoker.Invoke(new InvokerContext(
                new GetCurrentBill(),
                new { CompanyName = Session["CompanyName"] }
            ));
            ViewBag.Messages = context.Messages;
            return PartialView(context.Results.CurrentBill);
        }

        public JsonResult Unregister()
        {
            var context = invoker.Invoke(new InvokerContext(
                new UserUnregistration(),
                new { CompanyName = Session["CompanyName"] }
            ));

            if (!context.Results.UserUnregistered)
                return ControllerExtensions.CreateModelErrors(this, context.Messages.ToArray());

            TempData["Messages"] = context.Messages;
            FormsAuthentication.SignOut();
            return Json(new { Success = true, RedirectURL = Url.Action("Index", "Account") });
        }
    }
}
