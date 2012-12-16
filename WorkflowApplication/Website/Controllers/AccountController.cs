using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using System.Web.Security;
using Website.Infrastructure;
using Website.Models;
using Workflows;

namespace Website.Controllers
{
    public class AccountController : Controller
    {
        private IInvoker invoker;

        public AccountController(IInvoker invoker)
        {
            this.invoker = invoker;
        }

        public ActionResult Index()
        {
            return View();
        }

        public PartialViewResult Login()
        {
            return PartialView(new Credentials());
        }

        [HttpPost]
        public JsonResult Login(Credentials credentials)
        {
            if (!ModelState.IsValid)
                return ControllerExtensions.CreateModelValidationResult(this);

            var context = invoker.Invoke(new InvokerContext(new UserLogin(), credentials));

            if (!context.Results.LoggedIn)
                return ControllerExtensions.CreateModelErrors(this, context.Messages.ToArray());

            TempData["Messages"] = context.Messages;

            FormsAuthentication.SetAuthCookie(credentials.CompanyName, false);
            Session["CompanyName"] = credentials.CompanyName;
            return Json(new { Success = true, RedirectURL = Url.Action("Index", "Home") });
        }

        public PartialViewResult Register()
        {
            return PartialView(new Credentials());
        }

        [HttpPost]
        public JsonResult Register(Credentials credentials)
        {
            if (!ModelState.IsValid)
                return ControllerExtensions.CreateModelValidationResult(this);

            var context = invoker.Invoke(new InvokerContext(new UserRegistration(), credentials));

            if (!context.Results.UserRegistered)
                return ControllerExtensions.CreateModelErrors(this, context.Messages.ToArray());

            TempData["Messages"] = context.Messages;

            return Json(new { Success = true, RedirectURL = Url.Action("Index") });
        }
    }
}
