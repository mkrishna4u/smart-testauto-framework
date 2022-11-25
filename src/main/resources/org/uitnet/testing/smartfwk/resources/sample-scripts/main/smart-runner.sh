#!/bin/bash
###############################################################################
# SmartTestAutoFramework
# Copyright 2021 and beyond
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#     http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# 
# Author - Madhav Krishna
###############################################################################
if [ "$1" == "run-regression-tests" ]; then
./scripts/unix/runRegressionTests.sh $2
elif [ "$1" == "run-regression-but-not-sequential-tests" ]; then
./scripts/unix/runRegressionButNotSequentialTests.sh $2
elif [ "$1" == "run-failed-tests" ]; then
./scripts/unix/runAllFailedTests.sh $2
elif [ "$1" == "run-smoke-tests" ]; then
./scripts/unix/runSmokeTests.sh $2
elif [ "$1" == "run-sanity-tests" ]; then
./scripts/unix/runSanityTests.sh $2
elif [ "$1" == "run-sequential-tests" ]; then
./scripts/unix/runSequentialTests.sh
elif [ "$1" == "run-temp-scenarios" ]; then
./scripts/unix/runTempScenarios.sh $2
elif [ "$1" == "start-debug-server" ]; then
./scripts/unix/startDebugServerForTempScenarios.sh $2
elif [ "$1" == "find-all-missing-stepdefs" ]; then
./scripts/unix/findAllMissingStepDefinitions.sh
elif [ "$1" == "find-missing-stepdefs-for-temp-scenarios" ]; then
./scripts/unix/findMissingStepDefinitionsForTempScenarios.sh
elif [ "$1" == "generate-report" ]; then
./scripts/unix/generateReport.sh
else
  echo "smart-runner does not support command option '$1'."
  echo "Supported command options:"
  echo "    run-regression-tests <num-parallel-threads>"
  echo "    run-regression-but-not-sequential-tests <num-parallel-threads>"
  echo "    run-failed-tests <num-parallel-threads>"
  echo "    run-smoke-tests <num-parallel-threads>"
  echo "    run-sanity-tests <num-parallel-threads>"
  echo "    run-sequential-tests"
  echo "    run-temp-scenarios <num-parallel-threads>"
  echo "    start-debug-server <num-parallel-threads>"
  echo "    find-all-missing-stepdefs"
  echo "    find-missing-stepdefs-for-temp-scenarios"
  echo "    generate-report"
fi 
