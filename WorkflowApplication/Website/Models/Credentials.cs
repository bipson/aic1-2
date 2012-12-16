using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Website.Models
{
    public sealed class Credentials
    {
        [Required(ErrorMessage="The company name is required.")]
        [Display(Name="Company Name")]
        public String CompanyName { get; set; }

        [Required(ErrorMessage="The password is required.")]
        [MinLength(3, ErrorMessage="The password has to be at least 3 characters long.")]
        [DataType(DataType.Password)]
        public String Password { get; set; }
    }
}