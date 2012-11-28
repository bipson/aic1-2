module WorkflowTests.Helpers

open Microsoft.VisualStudio.TestTools.UnitTesting
open System
open System.Activities
open System.Activities.Tracking
open System.Collections.Generic

[<Literal>]
let trackToOutput = true;

type LambdaTrackingParticipant (replyChannel) =
    inherit TrackingParticipant ()
    override this.Track (record : TrackingRecord, timeout : TimeSpan) =
        replyChannel record

let printRecord (record : TrackingRecord) =
    match record with
    | :? WorkflowInstanceRecord as r ->
        sprintf "%d WorkflowInstanceRecord, ActivityDefinitionID: %s, State: %s" r.RecordNumber r.ActivityDefinitionId r.State
    | :? ActivityScheduledRecord as r->
        sprintf "%d ActivityScheduledRecord, ChildActivity: %s" r.RecordNumber r.Child.Name
    | :? ActivityStateRecord as r ->
        sprintf "%d ActivityStateRecord, Activity: %s, State: %s" r.RecordNumber r.Activity.Name r.State
    | :? BookmarkResumptionRecord as r ->
        sprintf "%d BookmarkResumptionRecord, Owner: %s, Bookmark: %s" r.RecordNumber r.Owner.Name 
            (let n = r.BookmarkName in if String.IsNullOrWhiteSpace n then "[empty]" else n)
    | _ -> record.ToString ()
    |> printfn "\t%s"

let grabRecords<'a when 'a :> TrackingRecord> records =
    records
    |> List.filter (fun (record : TrackingRecord) -> record :? 'a)
    |> List.map (fun (record : TrackingRecord) -> record :?> 'a)

let grabReferenceRecords =
    let referenceNames = 
        [ "Accounting"; "Payment"; "Sentiment"; "Twitter"; "User" ]
        |> List.map (sprintf "Workflows.%sReference.Activities")
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
    let tracker record =
        track.Add record
        if trackToOutput then printRecord record
    LambdaTrackingParticipant tracker
    |> invoker.Extensions.Add
    inputs
    |> List.map (fun (name, value) -> name, box value)
    |> Map.ofList
    |> invoker.Invoke, List.ofSeq track

let getResult name (results : #IDictionary<String,Object>) : 'a =
    results.[name] :?> 'a

let assertEqual expected actual =
    Assert.AreEqual (expected, actual)