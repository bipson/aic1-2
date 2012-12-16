namespace Website.Infrastructure

open System
open System.Activities
open System.Activities.Tracking
open System.Collections.Generic

type InvokerContext (workflow, parameters, messageProcessor, trackProcessor) =
    new (wf, ps) = let ig = Action<_> ignore in InvokerContext (wf, ps, ig, ig)
    member this.Workflow : Activity = workflow
    member this.Parameters : Object = parameters
    member this.MessageProcessor : Action<String> = messageProcessor
    member this.TrackProcessor : Action<TrackingRecord> = trackProcessor
    [<System.Runtime.CompilerServices.Dynamic [| true |]>]
    member val Results : Object = null with get, set
    member val Messages : seq<String> = null with get, set
    member val Track : seq<TrackingRecord> = null with get, set
    member val IsFinished : Boolean = false with get, set

type IInvoker = abstract Invoke : InvokerContext -> InvokerContext

type CallbackTrackingParticipant (callback) =
    inherit TrackingParticipant ()
    member val WorkflowTrack = ResizeArray<_> () with get
    override this.Track (record, _) = 
        this.WorkflowTrack.Add record
        callback record

type CallbackTextWriter (callback) =
    inherit System.IO.TextWriter ()
    member val WorkflowMessages = ResizeArray<String> () with get
    override val Encoding = System.Text.Encoding.Default with get
    override this.WriteLine message =
        this.WorkflowMessages.Add message
        callback message

type ResultDynamicObject (values : IDictionary<_,_>) =
    inherit System.Dynamic.DynamicObject ()
    override this.TryGetMember (binder, result) =
        let exists = values.ContainsKey binder.Name
        result <- if exists then values.[binder.Name] else null
        exists

type Invoker () =
    interface IInvoker with
        member this.Invoke ctx =
            let invoker = WorkflowInvoker ctx.Workflow
            let addEx v = v |> box |> invoker.Extensions.Add; v

            let inputs = ctx.Parameters.GetType().GetProperties()
                      |> Seq.map (fun pi -> pi.Name, pi.GetValue ctx.Parameters)
                      |> Map.ofSeq

            let tracker = CallbackTrackingParticipant ctx.TrackProcessor.Invoke |> addEx
            use writer = new CallbackTextWriter (ctx.MessageProcessor.Invoke) |> addEx

            ctx.Results  <- ResultDynamicObject (invoker.Invoke inputs) :> obj
            ctx.Track    <- tracker.WorkflowTrack
            ctx.Messages <- writer.WorkflowMessages
            ctx.IsFinished <- true
            ctx