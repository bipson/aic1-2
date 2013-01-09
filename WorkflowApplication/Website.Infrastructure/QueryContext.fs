namespace Website.Infrastructure

open System
open System.Collections.Generic
open System.Configuration
open System.Threading

type QueryContext (invokerContext, messageQueue, activityEvent) =
    let setting (s : String) = ConfigurationManager.AppSettings.[s]
    member this.InvokerContext : InvokerContext = invokerContext
    member this.MessageQueue   : Queue<String>  = messageQueue
    member this.ActivityEvent  : AutoResetEvent = activityEvent
    member this.WaitTimeout = setting "SentimentQueryWaitTimeout" |> Convert.ToInt32
    member val WaitRetries = setting "SentimentQueryWaitRetries" |> Convert.ToInt32 with get, set