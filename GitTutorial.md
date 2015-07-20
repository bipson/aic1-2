


---


---


# General Remarks #

This tutorial refers to the git-bash as environment for executing git commands. It is recommended to use the commandline in contrast to graphical tools in order to employ the full functionality.

Before executing any of the commands below, make sure to navigate to the root of the repository, e.g. the folder in which the project has been cloned.

Also, for a better understanding of the whole merging/branching concept I highly recommend the official documentation, easy to understand and illustrated with pictures:

http://git-scm.com/book/en/Git-Branching-Basic-Branching-and-Merging

and

http://git-scm.com/book/ch3-5.html


---


---


# Basic Commands #

## Retrieving the repo status ##

The most common and probably most used command is
```
git status
```
It tells you about the status of your local repository, if there are files that have been modified since the last commit ("dirty" files), new files (untracked files) or files that are staged (prepared) for commit.

## Committing the project snapshot ##

The commit runs in two stages:


---

  1. Adding files to the index (_stage_ files)

With the command
```
git add <filename>
```
you add the specified filename to the index. Instead of providing a filename you can use wildcards as well, e.g.
```
git add test*
```
will add any files and directories starting with "test" to the index. To add all (new or modified) files in the current directory, type
```
git add .
```


---

> 2. Commit all previously staged files to your local repository (_track_ files)

The actual commit is done with the command
```
git commit
```
In this phase all previously staged files will be committed to the local repository. When executing this command, a text editor will open up where you have to specify a commit message (mandatory). If you omit the commit message, the commit will be aborted.

A more convenient command is
```
git commit -a
```
which will automatically add all modified files to the index and start the commit procedure (open a text editor).

To finish the commit in one line, type
```
git commit -a -m "message"
```
where you provide the commit message directly, and no other action is required.


## Pushing your changes ##

Up to this point only local commands were issued, we haven't interacted with the remote repository at all. This also means that no one else sees what you have committed.

To actually push your changes to the remote repository, execute
```
git push
```
If there are any local commits which have not yet been pushed to the repository, the remote repository will be updated with your latest changes.


## Pulling changes ##

To update your local copy of the repository, type
```
git pull
```
This pulls all recent changes from other people working on the project into your local repository and automatically merges the changes with your local copy.


## Fetching changes ##

To update your local copy of the repository, type
```
git fetch
```
This will fetch all recent changes from the repository but without merging them into your local copy. This is the more careful way of getting the current project state without changing your local repo.


## Merging changes ##

If you have fetched updates from the repo and want to merge them with your local repo, type
```
git merge <server/branch>
```
The standard server name is `origin` and the standard branch name is `master`.
To merge updates from the master branch, you would type
```
git merge origin/master
```
More about merging and branches you'll find below.


---


---


# Branching in Git #

Branches in git are a really helpful tool as soon as you know how it works.

The whole point of branching is to have completely seperated workspaces in your project to work on, and you can switch between those "branches" whenever you like. Imagine you want to try something out which might break the code or you want to work on a feature seperately. Then you just create a new branch, do your work, commit as often as you like and if you really break something, either go back to a previous commit (revert) or switch back to the original branch and you are in the exact same state as you were when you created the branch.

## Local branching ##

Now, how do you create a branch? You might have guessed it already, it's
```
git branch <FancyBranch>
```

To switch to your newly created branch, type
```
git checkout <FancyBranch>
```

A shortcut to create a branch and switch to it in one step is
```
git checkout -b <FancyBranch>
```

If you have done that you might have noticed a change in the cmdline, it will display the name of your newly created branch in brackets at the end of the line.

To delete your branch right away, do
```
git branch -d <FancyBranch>
```


---


To get a list of branches that currently exist (locally), just do
```
git branch
```

To display the remote branches as well, type
```
git branch -a
```

The name of the remotes is constructed like this:
```
remotes/<server-alias>/<branch-name>
```

For example:
```
remotes/origin/master
```

## Remote branching ##

Now, if you want others to see your branch, you have to push it into the repository.

Do it like this:
```
git push <remote> <branch>
```

With the branch created above, it looks like this:
```
git push origin FancyBranch
```

If your teammembers type
```
git fetch origin
```
which means that they get all updates from the origin server, they will see a new remote branch showing up when executing the command
```
git branch -a
```


---


The remote branch is all fine and good, but you may want a local copy of that branch to have a look at the files it contains. This is called a "tracking branch", since it keeps track of its remote counterpart.

To finally create this branch locally, type
```
git checkout --track <server-alias>/<branch-name>
```

In our case it might look like
```
git checkout --track origin/FancyBranch
```

This will create the branch and switch to it automatically.

If you are inside a tracking branch, you can just use
```
git push
```
and
```
git pull
```
without any further parameters. Git will know to which remote branch to push/pull the changes.