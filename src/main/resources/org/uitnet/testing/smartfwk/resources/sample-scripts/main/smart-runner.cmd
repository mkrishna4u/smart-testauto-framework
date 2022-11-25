@echo off
REM ###############################################################################
REM # SmartTestAutoFramework
REM # Copyright 2021 and beyond
REM # 
REM # Licensed under the Apache License, Version 2.0 (the "License");
REM # you may not use this file except in compliance with the License.
REM # You may obtain a copy of the License at
REM # 
REM #     http://www.apache.org/licenses/LICENSE-2.0
REM # 
REM # Unless required by applicable law or agreed to in writing, software
REM # distributed under the License is distributed on an "AS IS" BASIS,
REM # WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
REM # See the License for the specific language governing permissions and
REM # limitations under the License.
REM # 
REM # Author - Madhav Krishna
REM ###############################################################################

set args=%*
if "%1"=="run-regression-tests" (
  call scripts\windows\runRegressionTests.cmd %2
) else if "%1"=="run-regression-but-not-sequential-tests" (
  call scripts\windows\runRegressionButNotSequentialTests.cmd %2
) else if "%1"=="run-failed-tests" (
  call scripts\windows\runAllFailedTests.cmd %2
) else if "%1"=="run-smoke-tests" (
  call scripts\windows\runSmokeTests.cmd %2
) else if "%1"=="run-sanity-tests" (
  call scripts\windows\runSanityTests.cmd %2
) else if "%1"=="run-sequential-tests" (
  call scripts\windows\runSequentialTests.cmd %2
) else if "%1"=="run-temp-scenarios" (
  call scripts\windows\runTempScenarios.cmd %2
) else if "%1"=="start-debug-server" (
  call scripts\windows\startDebugServerForTempScenarios.cmd %2
) else if "%1"=="find-all-missing-stepdefs" (
  call scripts\windows\findAllMissingStepDefinitions.cmd
) else if "%1"=="find-missing-stepdefs-for-temp-scenarios" (
  call scripts\windows\findMissingStepDefinitionsForTempScenarios.cmd
) else if "%1"=="generate-report" (
  call scripts\windows\generateReport.cmd
) else (
  echo smart-runner does not support command option '%1'.
  echo Supported command options:
  echo     run-regression-tests ^<num-parallel-threads^>
  echo     run-regression-but-not-sequential-tests ^<num-parallel-threads^>
  echo     run-failed-tests ^<num-parallel-threads^>
  echo     run-smoke-tests ^<num-parallel-threads^>
  echo     run-sanity-tests ^<num-parallel-threads^>
  echo     run-sequential-tests
  echo     run-temp-scenarios ^<num-parallel-threads^>
  echo     start-debug-server ^<num-parallel-threads^>
  echo     find-all-missing-stepdefs
  echo     find-missing-stepdefs-for-temp-scenarios
  echo     generate-report
)