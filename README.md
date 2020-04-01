# jenkins-pipeline-devops
Base in declarative pipelines

Basic you dont need to have jenkins files inside Jenkins and need to copy for all your envs for example, you ask your jenkins to take it from your lib (shared library) and after you can just call your fuction inside any of your jobs

    for example
    @Library('jenkins-library-sre') _
runDeploy('app_name')


Remember in my pipeline I have a app_name variable that you should replace inside your job as well.
