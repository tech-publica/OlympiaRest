package com.example.olympia.abstractions.dao;

import com.example.olympia.entity.Client;

public interface ClientDAO {
   Client findById(int id);
}
