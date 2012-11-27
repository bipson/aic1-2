namespace WorkflowTests

open Microsoft.VisualStudio.TestTools.UnitTesting
open Helpers
open System
open System.Activities.Tracking
open Workflows

[<Sealed; TestClass>]
type UserLoginTests () =

    [<TestMethod>]
    member this.``Try Logging In Nonexisting Company`` () =
        let results, track =
            UserLogin ()
            |> invokeWith [ ("companyName", "company4"); ("password", "c4password") ]

        results
        |> getResult "loggedIn"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "CheckCredentials" ]

    [<TestMethod>]
    member this.``Try Logging In Company With Wrong Password`` () =
        let results, track =
            UserLogin ()
            |> invokeWith [ ("companyName", "company1"); ("password", "c1wrongpassword") ]

        results
        |> getResult "loggedIn"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "CheckCredentials" ]

    [<TestMethod>]
    member this.``Login Company`` () =
        let results, track =
            UserLogin ()
            |> invokeWith [ ("companyName", "company1"); ("password", "c1password") ]

        results
        |> getResult "loggedIn"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "CheckCredentials" ]