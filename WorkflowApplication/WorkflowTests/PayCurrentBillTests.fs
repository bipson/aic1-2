namespace WorkflowTests

open Microsoft.VisualStudio.TestTools.UnitTesting
open Helpers
open System
open System.Activities.Tracking
open Workflows

[<Sealed; TestClass>]
type PayCurrentBillTests () =

    [<TestMethod>]
    member this.``Try Paying Bill Of Nonexisting Company`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("CompanyName", "company4"); ("UserAccountData", "{User Account Information}") ]

        results
        |> getResult "PaymentSucceeded"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill" ]

    [<TestMethod>]
    member this.``Pay Empty Bill`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("CompanyName", "company2"); ("UserAccountData", "{User Account Information}") ]

        results
        |> getResult "PaymentSucceeded"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill" ]

    [<TestMethod>]
    member this.``Try Paying Bill Where Settlement Fails`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("CompanyName", "company3"); ("UserAccountData", "{User Account Information}") ]

        results
        |> getResult "PaymentSucceeded"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill"; "GetOurAccountData"; "Charge" ]

    [<TestMethod>]
    member this.``Try Paying Bill Where Money Transfer Fails`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("CompanyName", "company1"); ("UserAccountData", "{Faulty User Account Information}") ]

        results
        |> getResult "PaymentSucceeded"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill"; "GetOurAccountData"; "Charge"; "Pay"; "Charge" ]

    [<TestMethod>]
    member this.``Pay Bill`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("CompanyName", "company1"); ("UserAccountData", "{User Account Information}") ]

        results
        |> getResult "PaymentSucceeded"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill"; "GetOurAccountData"; "Charge"; "Pay" ]