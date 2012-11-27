namespace WorkflowTests

open Microsoft.VisualStudio.TestTools.UnitTesting
open Helpers
open System
open System.Activities.Tracking
open Workflows

[<Sealed; TestClass>]
type UserRegistrationTests () =

    [<TestMethod>]
    member this.``Try Registering Existing Company`` () =
        let results, track =
            UserRegistration ()
            |> invokeWith [ ("companyName", "company1"); ("password", "c1password") ]

        results
        |> getResult "userRegistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists" ]

    [<TestMethod>]
    member this.``Try Registering Company Where Listener Attachment Fails`` () =
        let results, track =
            UserRegistration ()
            |> invokeWith [ ("companyName", "company3"); ("password", "c3password") ]
        
        results
        |> getResult "userRegistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Add"; "AttachListener"; "Remove" ]

    [<TestMethod>]
    member this.``Try Registering Company Where Adding Fails`` () =
        let results, track =
            UserRegistration ()
            |> invokeWith [ ("companyName", "company7"); ("password", "c7password") ]

        results
        |> getResult "userRegistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Add" ]

    [<TestMethod>]
    member this.``Register New Company`` () =
        let results, track =
            UserRegistration ()
            |> invokeWith [ ("companyName", "company4"); ("password", "c4password") ]

        results
        |> getResult "userRegistered"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Add"; "AttachListener" ]