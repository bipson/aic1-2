﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:4.0.30319.17929
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Workflows.TwitterReference {
    
    
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.ServiceContractAttribute(Namespace="http://aic.service.twitter/", ConfigurationName="TwitterReference.ITwitter")]
    public interface ITwitter {
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:FetchTweets", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Workflows.TwitterReference.FetchTweetsResponse FetchTweets(Workflows.TwitterReference.FetchTweets request);
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:FetchTweets", ReplyAction="*")]
        System.Threading.Tasks.Task<Workflows.TwitterReference.FetchTweetsResponse> FetchTweetsAsync(Workflows.TwitterReference.FetchTweets request);
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:ExtractText", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Workflows.TwitterReference.ExtractTextResponse ExtractText(Workflows.TwitterReference.ExtractText request);
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:ExtractText", ReplyAction="*")]
        System.Threading.Tasks.Task<Workflows.TwitterReference.ExtractTextResponse> ExtractTextAsync(Workflows.TwitterReference.ExtractText request);
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:DetachListener", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Workflows.TwitterReference.DetachListenerResponse DetachListener(Workflows.TwitterReference.DetachListener request);
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:DetachListener", ReplyAction="*")]
        System.Threading.Tasks.Task<Workflows.TwitterReference.DetachListenerResponse> DetachListenerAsync(Workflows.TwitterReference.DetachListener request);
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:AttachListener", ReplyAction="*")]
        [System.ServiceModel.XmlSerializerFormatAttribute(SupportFaults=true)]
        Workflows.TwitterReference.AttachListenerResponse AttachListener(Workflows.TwitterReference.AttachListener request);
        
        [System.ServiceModel.OperationContractAttribute(Action="urn:AttachListener", ReplyAction="*")]
        System.Threading.Tasks.Task<Workflows.TwitterReference.AttachListenerResponse> AttachListenerAsync(Workflows.TwitterReference.AttachListener request);
    }
    
    /// <remarks/>
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.Xml", "4.0.30319.17929")]
    [System.SerializableAttribute()]
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.ComponentModel.DesignerCategoryAttribute("code")]
    [System.Xml.Serialization.XmlTypeAttribute(Namespace="http://aic.service.twitter/")]
    public partial class tweet : object, System.ComponentModel.INotifyPropertyChanged {
        
        private long idField;
        
        private long reTweetsField;
        
        private string textField;
        
        private string userField;
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=0)]
        public long ID {
            get {
                return this.idField;
            }
            set {
                this.idField = value;
                this.RaisePropertyChanged("ID");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=1)]
        public long reTweets {
            get {
                return this.reTweetsField;
            }
            set {
                this.reTweetsField = value;
                this.RaisePropertyChanged("reTweets");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=2)]
        public string text {
            get {
                return this.textField;
            }
            set {
                this.textField = value;
                this.RaisePropertyChanged("text");
            }
        }
        
        /// <remarks/>
        [System.Xml.Serialization.XmlElementAttribute(Form=System.Xml.Schema.XmlSchemaForm.Unqualified, Order=3)]
        public string user {
            get {
                return this.userField;
            }
            set {
                this.userField = value;
                this.RaisePropertyChanged("user");
            }
        }
        
        public event System.ComponentModel.PropertyChangedEventHandler PropertyChanged;
        
        protected void RaisePropertyChanged(string propertyName) {
            System.ComponentModel.PropertyChangedEventHandler propertyChanged = this.PropertyChanged;
            if ((propertyChanged != null)) {
                propertyChanged(this, new System.ComponentModel.PropertyChangedEventArgs(propertyName));
            }
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="FetchTweets", WrapperNamespace="http://aic.service.twitter/", IsWrapped=true)]
    public partial class FetchTweets {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public string searchString;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=1)]
        public System.DateTime from;
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=2)]
        public System.DateTime to;
        
        public FetchTweets() {
        }
        
        public FetchTweets(string searchString, System.DateTime from, System.DateTime to) {
            this.searchString = searchString;
            this.from = from;
            this.to = to;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="FetchTweetsResponse", WrapperNamespace="http://aic.service.twitter/", IsWrapped=true)]
    public partial class FetchTweetsResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        [System.Xml.Serialization.XmlArrayAttribute()]
        [System.Xml.Serialization.XmlArrayItemAttribute("item", Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public Workflows.TwitterReference.tweet[] tweets;
        
        public FetchTweetsResponse() {
        }
        
        public FetchTweetsResponse(Workflows.TwitterReference.tweet[] tweets) {
            this.tweets = tweets;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="ExtractText", WrapperNamespace="http://aic.service.twitter/", IsWrapped=true)]
    public partial class ExtractText {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        [System.Xml.Serialization.XmlArrayAttribute()]
        [System.Xml.Serialization.XmlArrayItemAttribute("item", Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public Workflows.TwitterReference.tweet[] tweets;
        
        public ExtractText() {
        }
        
        public ExtractText(Workflows.TwitterReference.tweet[] tweets) {
            this.tweets = tweets;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="ExtractTextResponse", WrapperNamespace="http://aic.service.twitter/", IsWrapped=true)]
    public partial class ExtractTextResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        [System.Xml.Serialization.XmlArrayAttribute()]
        [System.Xml.Serialization.XmlArrayItemAttribute("item", Form=System.Xml.Schema.XmlSchemaForm.Unqualified)]
        public string[] texts;
        
        public ExtractTextResponse() {
        }
        
        public ExtractTextResponse(string[] texts) {
            this.texts = texts;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="DetachListener", WrapperNamespace="http://aic.service.twitter/", IsWrapped=true)]
    public partial class DetachListener {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public string filter;
        
        public DetachListener() {
        }
        
        public DetachListener(string filter) {
            this.filter = filter;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="DetachListenerResponse", WrapperNamespace="http://aic.service.twitter/", IsWrapped=true)]
    public partial class DetachListenerResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public bool listenerDetached;
        
        public DetachListenerResponse() {
        }
        
        public DetachListenerResponse(bool listenerDetached) {
            this.listenerDetached = listenerDetached;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="AttachListener", WrapperNamespace="http://aic.service.twitter/", IsWrapped=true)]
    public partial class AttachListener {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public string filter;
        
        public AttachListener() {
        }
        
        public AttachListener(string filter) {
            this.filter = filter;
        }
    }
    
    [System.Diagnostics.DebuggerStepThroughAttribute()]
    [System.CodeDom.Compiler.GeneratedCodeAttribute("System.ServiceModel", "4.0.0.0")]
    [System.ServiceModel.MessageContractAttribute(WrapperName="AttachListenerResponse", WrapperNamespace="http://aic.service.twitter/", IsWrapped=true)]
    public partial class AttachListenerResponse {
        
        [System.ServiceModel.MessageBodyMemberAttribute(Namespace="", Order=0)]
        public bool listenerAttached;
        
        public AttachListenerResponse() {
        }
        
        public AttachListenerResponse(bool listenerAttached) {
            this.listenerAttached = listenerAttached;
        }
    }
}
