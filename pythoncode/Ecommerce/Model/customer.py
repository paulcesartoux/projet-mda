#!/usr/bin/python
# -*- coding: utf-8 -*-

from User import User


class Customer(User):
    def __init__(self):
        self.adress = None
        self.shoppingCart = None
        self.orders = None

    def viewOrderHistory(self, ):
        pass
