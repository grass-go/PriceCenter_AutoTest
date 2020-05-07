#!/usr/bin/env bash
ps -ef | grep Applications/Google | grep -v grep | awk '{print $2}' | xargs kill -9