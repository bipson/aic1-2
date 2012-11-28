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
            |> invokeWith [ ("companyName", "company4"); ("userAccountData", "{User Account Information}") ]

        results
        |> getResult "paymentSucceeded"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill" ]

    [<TestMethod>]
    member this.``Pay Empty Bill`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("companyName", "company2"); ("userAccountData", "{User Account Information}") ]

        results
        |> getResult "paymentSucceeded"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill" ]

    [<TestMethod>]
    member this.``Try Paying Bill Where Settlement Fails`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("companyName", "company3"); ("userAccountData", "{User Account Information}") ]

        results
        |> getResult "paymentSucceeded"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill"; "GetOurAccountData"; "Charge" ]

    [<TestMethod>]
    member this.``Try Paying Bill Where Money Transfer Fails`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("companyName", "company1"); ("userAccountData", "{Faulty User Account Information}") ]

        results
        |> getResult "paymentSucceeded"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill"; "GetOurAccountData"; "Charge"; "Pay"; "Charge" ]

    [<TestMethod>]
    member this.``Pay Bill`` () =
        let results, track =
            PayCurrentBill ()
            |> invokeWith [ ("companyName", "company1"); ("userAccountData", "{User Account Information}") ]

        results
        |> getResult "paymentSucceeded"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "GetBill"; "GetOurAccountData"; "Charge"; "Pay" ]