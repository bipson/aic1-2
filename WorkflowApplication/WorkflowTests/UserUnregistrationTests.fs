namespace WorkflowTests

open Microsoft.VisualStudio.TestTools.UnitTesting
open Helpers
open System
open System.Activities.Tracking
open Workflows

[<Sealed; TestClass>]
type UserUnregistrationTests () =

    [<TestMethod>]
    member this.``Try Unregistering Not Registered Company`` () =
        let results, track =
            UserUnregistration ()
            |> invokeWith [ ("companyName", "company4") ]
        
        results
        |> getResult "userUnregistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists" ]

    [<TestMethod>]
    member this.``Try Unregistering Company With Pending Payment`` () =
        let results, track =
            UserUnregistration ()
            |> invokeWith [ ("companyName", "company1") ]

        results
        |> getResult "userUnregistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "GetBill" ]

    [<TestMethod>]
    member this.``Try Unregistering Company Where Listener Detachment Fails`` () =
        let results, track =
            UserUnregistration ()
            |> invokeWith [ ("companyName", "company2") ]

        results
        |> getResult "userUnregistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "GetBill"; "DetachListener" ]

    [<TestMethod>]
    member this.``Try Unregistering Company Where Removal Fails`` () =
        let results, track =
            UserUnregistration ()
            |> invokeWith [ ("companyName", "company5") ]

        results
        |> getResult "userUnregistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "GetBill"; "DetachListener"; "Remove"; "AttachListener" ]

    [<TestMethod>]
    member this.``Unregister Company`` () =
        let results, track =
            UserUnregistration ()
            |> invokeWith [ ("companyName", "company6") ]

        results
        |> getResult "userUnregistered"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "GetBill"; "DetachListener"; "Remove" ]