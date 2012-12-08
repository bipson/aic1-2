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
            |> invokeWith [ ("CompanyName", "company4"); ("Password", "c4password") ]

        results
        |> getResult "LoggedIn"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "CheckCredentials" ]

    [<TestMethod>]
    member this.``Try Logging In Company With Wrong Password`` () =
        let results, track =
            UserLogin ()
            |> invokeWith [ ("CompanyName", "company1"); ("Password", "c1wrongpassword") ]

        results
        |> getResult "LoggedIn"
        |> assertEqual false

        track
        |> grabReferenceRecords
        |> assertEqual [ "CheckCredentials" ]

    [<TestMethod>]
    member this.``Login Company`` () =
        let results, track =
            UserLogin ()
            |> invokeWith [ ("CompanyName", "company1"); ("Password", "c1password") ]

        results
        |> getResult "LoggedIn"
        |> assertEqual true

        track
        |> grabReferenceRecords
        |> assertEqual [ "CheckCredentials" ]