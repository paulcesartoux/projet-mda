#!/usr/bin/python
# -*- coding: utf-8 -*-

from user import User


class Customer(User):
    def __init__(self):
        self.adress = None
