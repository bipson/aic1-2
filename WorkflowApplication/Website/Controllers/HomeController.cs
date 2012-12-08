using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using Website.Infrastructure;
using Website.Models;
using Workflows;

namespace Website.Controllers
{
    public class HomeController : Controller
    {
        private IInvoker invoker;

        public HomeController(IInvoker invoker)
        {
            this.invoker = invoker;
        }

        [HttpGet]
        public ActionResult Index()
        {
            return View(new Credentials());
        }

        [HttpPost]
        public ActionResult Index(Credentials credentials)
        {
            var result = invoker.Invoke(new InvokerContext(
                new UserLogin(),
                new { credentials.CompanyName, credentials.Password }
            ));
            TempData["WorkflowMessages"] = result.Messages;
            if (result.Results.LoggedIn)
                return RedirectToAction("Index", "Overview");
            return View(credentials);
        }
    }
}
