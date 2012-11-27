module WorkflowTests.Helpers

open Microsoft.VisualStudio.TestTools.UnitTesting
open System
open System.Activities
open System.Activities.Tracking
open System.Collections.Generic

type LambdaTrackingParticipant (replyChannel) =
    inherit TrackingParticipant ()
    override this.Track (record : TrackingRecord, timeout : TimeSpan) =
        replyChannel record

let isStateRecord (record : TrackingRecord) =
    if record :? ActivityStateRecord then Some record else None

let invokeWith inputs tracker activity =
    let invoker = WorkflowInvoker activity
    LambdaTrackingParticipant tracker
    |> invoker.Extensions.Add
    inputs
    |> List.map (fun (name, value) -> name, box value)
    |> Map.ofList
    |> invoker.Invoke

let getResult name (results : #IDictionary<String,Object>) : 'a =
    results.[name] :?> 'a

let assertEqual expected actual =
    Assert.AreEqual (expected, actual)