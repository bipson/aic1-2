namespace WorkflowTests

open Microsoft.VisualStudio.TestTools.UnitTesting
open Helpers
open System
open System.Activities.Tracking
open Workflows

[<Sealed; TestClass>]
type QuerySentimentTests () =

    let dateFrom = DateTime (2012, 01, 01) |> box
    let dateTo = DateTime (2012, 02, 01) |> box

    [<TestMethod>]
    member this.``Try Querying Sentiment Of Nonregistered Company`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("companyName", box "company4"); ("dateFrom", dateFrom); ("dateTo", dateTo) ]

        results
        |> getResult "sentiment"
        |> assertEqual Double.NaN

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists" ]

    [<TestMethod>]
    member this.``Try Querying Sentiment Where Charging Fails`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("companyName", box "company5"); ("dateFrom", dateFrom); ("dateTo", dateTo) ]

        results
        |> getResult "sentiment"
        |> assertEqual Double.NaN

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Charge" ]

    [<TestMethod>]
    member this.``Try Querying Sentiment Where No Tweets Are Returned`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("companyName", box "company6"); ("dateFrom", dateFrom); ("dateTo", dateTo) ]

        results
        |> getResult "sentiment"
        |> assertEqual Double.NaN

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Charge"; "FetchTweets"; "ExtractText" ]

    [<TestMethod>]
    member this.``Query Low Sentiment`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("companyName", box "company1"); ("dateFrom", dateFrom); ("dateTo", dateTo) ]

        results
        |> getResult "sentiment"
        |> assertEqual 0.4

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Charge"; "FetchTweets"; "ExtractText"; "ComputeAverage" ]

    [<TestMethod>]
    member this.``Query High Sentiment`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("companyName", box "company2"); ("dateFrom", dateFrom); ("dateTo", dateTo) ]

        results
        |> getResult "sentiment"
        |> assertEqual 0.7

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Charge"; "FetchTweets"; "ExtractText"; "ComputeAverage" ]