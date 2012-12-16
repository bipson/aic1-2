namespace Website.Infrastructure

open System
open System.Collections.Generic
open System.Threading

type QueryContext (invokerContext, messageQueue, activityEvent) =
    member this.InvokerContext : InvokerContext = invokerContext
    member this.MessageQueue   : Queue<String>  = messageQueue
    member this.ActivityEvent  : AutoResetEvent = activityEvent