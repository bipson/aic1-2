namespace WorkflowTests

open Microsoft.VisualStudio.TestTools.UnitTesting
open Helpers
open System
open System.Activities.Tracking
open Workflows

[<Sealed; TestClass>]
type GetCurrentBillTests () =

    [<TestMethod>]
    member this.``Try Getting Bill Of Nonexisting Company`` () =
        let results, track =
            GetCurrentBill ()
            |> invokeWith [ ("CompanyName", "company4") ]

        results
        |> getResult "CurrentBill"
        |> assertEqual Double.NaN

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill" ]

    [<TestMethod>]
    member this.``Get Empty Bill`` () =
        let results, track =
            GetCurrentBill ()
            |> invokeWith [ ("CompanyName", "company2") ]

        results
        |> getResult "CurrentBill"
        |> assertEqual 0.0

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill" ]

    [<TestMethod>]
    member this.``Get Nonempty Bill`` () =
        let results, track =
            GetCurrentBill ()
            |> invokeWith [ ("CompanyName", "company1") ]

        results
        |> getResult "CurrentBill"
        |> assertEqual 34.5

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill" ]
