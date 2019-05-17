package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class GT4500Test {

  @Mock TorpedoStore primaryTorpedoStore;
  @Mock TorpedoStore secondaryTorpedoStore;
  @InjectMocks private GT4500 ship;

  @BeforeEach
  public void init(){
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void shouldFirePrimaryTorpedoStore() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    assertEquals(true, ship.fireTorpedo(FiringMode.SINGLE));
  }

  @Test
  public void shouldFireSecondaryTorpedo() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    assertEquals(true, ship.fireTorpedo(FiringMode.SINGLE));
  }

  @Test
  public void shouldNotFireTorpedo() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    assertEquals(false, ship.fireTorpedo(FiringMode.SINGLE));
  }

  @Test
  public void shouldFireAlternating() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    assertEquals(true, ship.fireTorpedo(FiringMode.SINGLE));
    assertEquals(true, ship.fireTorpedo(FiringMode.SINGLE));
  }

  @Test
  public void shouldFirePrimaryOnly() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    assertEquals(true, ship.fireTorpedo(FiringMode.ALL));
  }

  // based on code
  @Test
  public void shouldNotFireAtAll() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    assertEquals(false, ship.fireTorpedo(FiringMode.ALL));
  }

  @Test
  public void shouldNotFireLaser() {
    assertEquals(false, ship.fireLaser(FiringMode.ALL));
    assertEquals(false, ship.fireLaser(FiringMode.SINGLE));
  }

  @Test
  public void shouldFirePrimaryTwice() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);
    assertEquals(true, ship.fireTorpedo(FiringMode.SINGLE));
  }

  @Test
  public void shouldFireAllTorpedos() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    assertEquals(true, ship.fireTorpedo(FiringMode.ALL));
  }

  @Test
  public void shouldFireAllTorpedosStartingWithSecondary() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    assertEquals(true, ship.fireTorpedo(FiringMode.SINGLE));
    assertEquals(true, ship.fireTorpedo(FiringMode.ALL));
  }

  @Test
  public void shouldFailWhenTryingToFirePrimaryTwice() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    assertEquals(false, ship.fireTorpedo(FiringMode.SINGLE));
  }

  @Test
  public void shouldNotFireAtAll_primaryLast() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
    assertEquals(false, ship.fireTorpedo(FiringMode.ALL));
  }

  @Test
  public void shouldFireSecondaryOnly_primarylast() {
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    ship.fireTorpedo(FiringMode.SINGLE);
    when(primaryTorpedoStore.isEmpty()).thenReturn(true);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    assertEquals(true, ship.fireTorpedo(FiringMode.ALL));
  }
}
