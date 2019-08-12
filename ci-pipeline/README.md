# Android CI pipeline

This repository contains a reference CI pipeline for building an android app.

##  GitHub connections

- Selet Settings of the project >> GitHub connections.
- Sign in to your GitHub repo
- User PAT to give the Azure DevOps access to your account.
- Click Authorize AzureBoards.

## Setup

- Create a project in Azure Devops.
- Once the project is created, selects Build from left panel and New -->> New Build Pipeline .
- Select the GitHub repo you want to build.
- Input the `android-ci-pipeline.yaml` in the pipeline yaml.
- Go to Triggers and tick the Enable continuous integration.
- Save and Queue.

## Usage

- Pipeline will first checkout and build the Repo.
- Control Options Enabled will fail the build in case of any Compilation Error.


## Note

- `system.debug=true` is used to get a verbode logs for the build pipeline.
- `BuildConfiguration=debug` creates a snapshot version of the code.