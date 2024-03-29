#!/bin/bash
###############################################################################
# SmartTestAutoFramework
# Copyright 2021 and beyond [Madhav Krishna]
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
clear

num_threads=1
if [ "$3" != "" ]; then
  num_threads=$3
fi

tags="$1"
if [ "$tags" != "" ]; then
  tags="($tags) and not @Pending"
else
  echo ERROR: No tags found.
fi

env_name="$2"
if [ "$env_name" == "" ]; then
  echo ERROR: No environment found.
fi

echo "PARALLEL_THREADS = $num_threads"
echo "Executing: Tags=$tags"
echo "Environment: $env_name"
mvn clean verify -Dcucumber.filter.tags="$tags" -Dapps.active.environment="$env_name" -Dparallel.threads=$num_threads
