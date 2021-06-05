#!/bin/bash -v

mkdir jqwik

mvn clean install && mvn source:jar install
