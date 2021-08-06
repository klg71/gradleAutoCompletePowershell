# Gradle Autocomplete for Powershell
The autocompletion works by creating a `.tasks` file in the project folder at the first time it is executed.  
Following executions will use this file to determine the matching task.  
If you need to refresh the task list simply delete the `.tasks` file

## Usage
1. Download the `gradleAutoComplete.jar` and `gradleAutoComplete.bat` 
2. Add the containing folder to the `PATH` Environment variable
3. Set ExecutionPolicy in Powershell to `RemoteSigned`:
   1. Run Powershell as Administrator
   2. Run `Set-ExecutionPolicy RemoteSigned`
4. Add the following snippet to your $PROFILE in the Powershell (`notepad.exe $PROFILE`)

```powershell
# The code that will perform the auto-completion
$scriptBlock = {
    # The parameters passed into the script block by the
    #  Register-ArgumentCompleter command
    param(
            $commandName, $parameterName, $wordToComplete,
            $commandAst, $fakeBoundParameters
    )

    # The list of values that the typed text is compared to
    $values = ''
    if (Test-Path './gradlew.bat' -PathType leaf){
        if(Test-Path './.tasks' -PathType leaf){
            $values = Get-Content -Path './.tasks'
        }else{
            $pwd= pwd
            try{
                $values = gradleAutoComplete $pwd
            }catch{
                Write-Host "An error occurred:"
                Write-Host $_
            }
            Out-File -FilePath './.tasks' -InputObject $values -Encoding ASCII
        }
    }
    $valuesSplit=$values.Split([Environment]::NewLine)


    foreach ($val in $values) {
        # What has been typed matches the value from the list
        if ($val -like "$wordToComplete*") {
            # Print the value
            $val
        }
    }
}

# Register our function and auto-completion code with the command
Register-ArgumentCompleter -CommandName gradlew -ParameterName Param -ScriptBlock $scriptBlock
```

5. Run `. $PROFILE` to reload your configuration
6. Now you can navigate into your gradle folder and type `gradlew taskNa<tab>` to trigger the autocompletion (if you have a lot of tasks it could take some time at the first trigger)
