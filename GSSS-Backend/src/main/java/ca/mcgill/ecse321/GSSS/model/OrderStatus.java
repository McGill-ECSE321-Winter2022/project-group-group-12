package ca.mcgill.ecse321.GSSS.model;

/**
 * 
 * enumeration OrderStatus, used by Order class to define the order status for orders of type
 * delivery, status goes through all three for orders of type pick-up, it goes from beingPrepared to
 * completed, since there is no need for delivery
 *
 * @author Habib Jarweh
 *
 */

public enum OrderStatus {

  beingPrepared, outForDelivery, completed
}
