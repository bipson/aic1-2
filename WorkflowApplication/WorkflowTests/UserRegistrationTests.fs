namespace WorkflowTests

open Microsoft.VisualStudio.TestTools.UnitTesting
open Helpers
open System
open Workflows

[<Sealed; TestClass>]
type UserRegistrationTests () =

    [<TestMethod>]
    member this.``Try Registering Existing Company`` () =
        UserRegistration ()
        |> invokeWith [ ("companyName", "company1"); ("password", "c1password") ] (printfn "%O")
        |> getResult "userRegistered"
        |> assertEqual false

    [<TestMethod>]
    member this.``Try Registering Company Where Listener Attachment Fails`` () =
        UserRegistration ()
        |> invokeWith [ ("companyName", "company3"); ("password", "c3password") ] (printfn "%O")
        |> getResult "userRegistered"
        |> assertEqual false

    [<TestMethod>]
    member this.``Register New Company`` () =
        UserRegistration ()
        |> invokeWith [ ("companyName", "company4"); ("password", "c4password") ] (printfn "%O")
        |> getResult "userRegistered"
        |> assertEqual true