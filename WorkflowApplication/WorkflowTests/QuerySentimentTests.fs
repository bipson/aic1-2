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
            |> invokeWith [ ("CompanyName", box "company4"); ("DateFrom", dateFrom); ("DateTo", dateTo) ]

        results
        |> getResult "Sentiment"
        |> assertEqual Double.NaN

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists" ]

    [<TestMethod>]
    member this.``Try Querying Sentiment Where Charging Fails`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("CompanyName", box "company5"); ("DateFrom", dateFrom); ("DateTo", dateTo) ]

        results
        |> getResult "Sentiment"
        |> assertEqual Double.NaN

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Charge" ]

    [<TestMethod>]
    member this.``Try Querying Sentiment Where No Tweets Are Returned`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("CompanyName", box "company6"); ("DateFrom", dateFrom); ("DateTo", dateTo) ]

        results
        |> getResult "Sentiment"
        |> assertEqual Double.NaN

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Charge"; "FetchTweets"; "ExtractText" ]

    [<TestMethod>]
    member this.``Query Low Sentiment`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("CompanyName", box "company1"); ("DateFrom", dateFrom); ("DateTo", dateTo) ]

        results
        |> getResult "Sentiment"
        |> assertEqual 0.4

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Charge"; "FetchTweets"; "ExtractText"; "ComputeAverage" ]

    [<TestMethod>]
    member this.``Query High Sentiment`` () =
        let results, track =
            QuerySentiment ()
            |> invokeWith [ ("CompanyName", box "company2"); ("DateFrom", dateFrom); ("DateTo", dateTo) ]

        results
        |> getResult "Sentiment"
        |> assertEqual 0.7

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Charge"; "FetchTweets"; "ExtractText"; "ComputeAverage" ]