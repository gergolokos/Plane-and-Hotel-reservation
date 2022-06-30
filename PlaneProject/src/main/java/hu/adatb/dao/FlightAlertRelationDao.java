package hu.adatb.dao;


import hu.adatb.model.FlightAlertRelation;

import java.util.List;

public interface FlightAlertRelationDao {
    public boolean add (FlightAlertRelation relation);

    public List<FlightAlertRelation> getAll();
}
