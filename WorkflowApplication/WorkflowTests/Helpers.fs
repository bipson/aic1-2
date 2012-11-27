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

let grabRecords<'a when 'a :> TrackingRecord> records =
    records
    |> List.filter (fun (record : TrackingRecord) -> record :? 'a)
    |> List.map (fun (record : TrackingRecord) -> record :?> 'a)

let grabReferenceRecords =
    let referenceNames = 
        [ 
            "Workflows.AccountingReference.Activities"
            "Workflows.PaymentReference.Activities"
            "Workflows.SentimentReference.Activities"
            "Workflows.TwitterReference.Activities"
            "Workflows.UserReference.Activities" 
        ]
    grabRecords<ActivityStateRecord>
    >> List.filter (fun record ->
        record.State = "Executing" &&
        List.exists record.Activity.TypeName.StartsWith referenceNames)
    >> List.map (fun record ->
        let typeName = record.Activity.TypeName
        typeName.LastIndexOf '.' |> (+) 1 |> typeName.Substring)

let invokeWith inputs activity =
    let invoker = WorkflowInvoker activity
    let track = List<TrackingRecord> ()
    LambdaTrackingParticipant track.Add
    |> invoker.Extensions.Add
    inputs
    |> List.map (fun (name, value) -> name, box value)
    |> Map.ofList
    |> invoker.Invoke, List.ofSeq track

let getResult name (results : #IDictionary<String,Object>) : 'a =
    results.[name] :?> 'a

let assertEqual expected actual =
    Assert.AreEqual (expected, actual)