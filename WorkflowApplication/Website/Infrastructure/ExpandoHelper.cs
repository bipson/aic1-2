using System;
using System.Collections.Generic;
using System.Dynamic;
using System.Linq;
using System.Web;
using System.Web.Routing;

namespace Website.Infrastructure
{
    public static class ExpandoHelper
    {
        public static ExpandoObject ToExpando(this Object anonymousObject)
        {
            var dict = new RouteValueDictionary(anonymousObject);
            IDictionary<String, Object> eo = new ExpandoObject();
            foreach (var item in dict) eo.Add(item);
            return (ExpandoObject)eo;
        }
    }
}