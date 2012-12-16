using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Website.Infrastructure
{
    public static class ControllerExtensions
    {
        public static JsonResult CreateModelValidationResult(this Controller controller)
        {
            return new JsonResult 
            { 
                Data =
                    from me in controller.ModelState
                    where me.Value.Errors.Count > 0
                    select new
                    {
                        Name = me.Key,
                        Error = me.Value.Errors.First().ErrorMessage
                    }
            };
        }

        public static JsonResult CreateModelErrors(this Controller controller, params String[] errors)
        {
            return new JsonResult
            {
                Data =
                    from error in errors
                    select new
                    {
                        Name = String.Empty,
                        Error = error
                    }
            };
        }
    }
}