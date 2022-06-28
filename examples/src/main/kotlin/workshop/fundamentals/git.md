# GIT COMMANDS
    
## initialization and configuration

Configure your personal name for your commits.

    git config --global user.name “[firstname lastname]”


Configure your personal email for your commits.

    git config --global user.email “[valid-email]”

Initialize a new git repository in the current directory
    
    git init

Clone an existing GIT repository to the current directory

    git clone [REMOTE URL]

## Current stage

Get the current git status. Changed files, staged files, current branch.
    
    git status

Add changed filed or all changed files in a directory to the stage. 

    git add [file|directory]

Create a new commit with the staged files and a nice commit message.

    git commit -m "My commit message"

Get the diff of the current file system and the stage.

    git diff
    
Get the diff of the stage and the last commit

    git diff --staged

## Branching

List all local branches
    
    git branch

Create a new branch at the current commit

    git branch [branch_name]

Switching to another branch

    git checkout [branch_name]

Merging a branch into the current branch

    git merge [branch]

Pushing the current branch and all changes to a remote

    git push [REMOTE NAME]

Creating a local branch on the remote repository

    git push --set-upstream origin foobar

Pulling all new changes from a remote repository
    
    git pull [REMOTE NAME]