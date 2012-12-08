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
            |> invokeWith [ ("CompanyName", "company1"); ("Password", "c1password") ]

        results
        |> getResult "UserRegistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists" ]

    [<TestMethod>]
    member this.``Try Registering Company Where Listener Attachment Fails`` () =
        let results, track =
            UserRegistration ()
            |> invokeWith [ ("CompanyName", "company3"); ("Password", "c3password") ]
        
        results
        |> getResult "UserRegistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Add"; "AttachListener"; "Remove" ]

    [<TestMethod>]
    member this.``Try Registering Company Where Adding Fails`` () =
        let results, track =
            UserRegistration ()
            |> invokeWith [ ("CompanyName", "company7"); ("Password", "c7password") ]

        results
        |> getResult "UserRegistered"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Add" ]

    [<TestMethod>]
    member this.``Register New Company`` () =
        let results, track =
            UserRegistration ()
            |> invokeWith [ ("CompanyName", "company4"); ("Password", "c4password") ]

        results
        |> getResult "UserRegistered"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "Exists"; "Add"; "AttachListener" ]