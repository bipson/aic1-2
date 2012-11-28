﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.17929
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Workflows.PaymentReference {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace="http://aic.service.payment/", ConfigurationName="PaymentReference.IPayment")]
    public interface IPayment {
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:Pay", ReplyAction="*")]
        Workflows.PaymentReference.PayResponse Pay(Workflows.PaymentReference.Pay request);
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:Pay", ReplyAction="*")]
        System.Threading.Tasks.Task<Workflows.PaymentReference.PayResponse> PayAsync(Workflows.PaymentReference.Pay request);
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="Pay", WrapperNamespace="http://aic.service.payment/", IsWrapped=true)]
    public partial class Pay {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public string ourAccountData;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=1)]
        public string userAccountData;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=2)]
        public double amount;
        
        public Pay() {
        }
        
        public Pay(string ourAccountData, string userAccountData, double amount) {
            this.ourAccountData = ourAccountData;
            this.userAccountData = userAccountData;
            this.amount = amount;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="PayResponse", WrapperNamespace="http://aic.service.payment/", IsWrapped=true)]
    public partial class PayResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public bool paymentSucceeded;
        
        public PayResponse() {
        }
        
        public PayResponse(bool paymentSucceeded) {
            this.paymentSucceeded = paymentSucceeded;
        }
    }
}
