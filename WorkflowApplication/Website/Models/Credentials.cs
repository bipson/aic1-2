using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Website.Models
{
    public sealed class Credentials
    {
        [Required]
        [MinLength(3)]
        public String CompanyName { get; set; }

        [Required]
        [MinLength(3)]
        [DataType(DataType.Password)]
        public String Password { get; set; }
    }
}