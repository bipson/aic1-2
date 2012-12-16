using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using System.Web.Mvc;

namespace Website.Models
{
    [Bind(Exclude="CurrentBill, CompanyName")]
    public class BillingInformation
    {
        [ScaffoldColumn(false)]
        public Double CurrentBill { get; set; }

        [Required]
        [Display(Name="Your Account Data")]
        public String UserAccountData { get; set; }
    }
}