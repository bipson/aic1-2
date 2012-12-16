using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;

namespace Website.Models
{
    public class DateInterval
    {
        [Required]
        [Display(Name="From")]
        [DataType(DataType.Date)]
        public DateTime DateFrom { get; set; }

        [Required]
        [Display(Name="To")]
        [DataType(DataType.Date)]
        public DateTime DateTo { get; set; }
    }
}