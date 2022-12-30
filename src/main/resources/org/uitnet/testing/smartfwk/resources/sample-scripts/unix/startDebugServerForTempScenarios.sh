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
# Debug server will get started at localhost port 5005.
clear

num_threads=1
if [ "$1" != "" ]; then
  num_threads=$1
fi

echo "DEGREE OF PARALLISM = $num_threads"

./set-env.sh &&  mvn clean verify -Dcucumber.filter.tags="@TempScenario and not @Pending" -Dmaven.surefire.debug=true -Dparallel.threads=$num_threads