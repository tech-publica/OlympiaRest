package com.example.olympia.abstractions.dao;

import com.example.olympia.entity.Court;

public interface CourtDAO {
     Court findById(int id);
}
