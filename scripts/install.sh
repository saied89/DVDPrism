#!/usr/bin/env bash

sudo cp backend/backend.service /etc/systemd/system/
sudo systemctl daemon-reload