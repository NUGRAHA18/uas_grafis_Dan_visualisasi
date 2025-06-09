// 3D Object Inisial AGU dengan kontrol interaktif
// Fitur: Pitch, Yaw, Roll, Movement, Zoom, Texture, Lighting

// Variabel kontrol rotasi dan posisi
float pitch = 0;
float yaw = 0;
float roll = 0;
float posX = 0;
float posY = 0;
float zoomLevel = 1.0;

// Variabel kontrol fitur
boolean textureEnabled = true;
boolean shadowEnabled = true;
PGraphics texture;

// Variabel lighting
float lightX = 100;
float lightY = -50;
float lightZ = 200;

// Variabel kontrol mouse
float lastMouseX, lastMouseY;
boolean isDragging = false;

void setup() {
  size(800, 600, P3D);
  
  // Buat texture sederhana
  texture = createGraphics(100, 100, P3D);
  texture.beginDraw();
  texture.background(100, 150, 255);
  texture.fill(255, 200, 100);
  texture.noStroke();
  for(int i = 0; i < 10; i++) {
    for(int j = 0; j < 10; j++) {
      if((i + j) % 2 == 0) {
        texture.rect(i * 10, j * 10, 10, 10);
      }
    }
  }
  texture.endDraw();
}

void draw() {
  background(40, 40, 60);
  
  // Setup lighting
  if(shadowEnabled) {
    ambientLight(50, 50, 50);
    directionalLight(255, 255, 255, lightX/100, lightY/100, lightZ/100);
    pointLight(255, 200, 150, lightX, lightY, lightZ);
  } else {
    ambientLight(150, 150, 150);
  }
  
  // Setup kamera dan transformasi
  translate(width/2 + posX, height/2 + posY, 0);
  scale(zoomLevel);
  
  // Apply rotasi
  rotateX(pitch);
  rotateY(yaw);
  rotateZ(roll);
  
  // Gambar objek 3D inisial "AGU"
  drawLetterA(-120, 0);
  drawLetterG(0, 0);
  drawLetterU(120, 0);
  
  // Gambar light source indicator
  if(shadowEnabled) {
    drawLightSource();
  }
  
  // Display controls info
  displayControls();
}

void drawLetterA(float offsetX, float offsetY) {
  pushMatrix();
  translate(offsetX, offsetY, 0);
  
  if(textureEnabled) {
    fill(255);
    textureMode(NORMAL);
  } else {
    fill(255, 100, 100);
  }
  
  // Batang kiri A
  pushMatrix();
  translate(-15, 0, 0);
  rotateZ(0.2);
  if(textureEnabled) texture(texture);
  box(10, 80, 20);
  popMatrix();
  
  // Batang kanan A
  pushMatrix();
  translate(15, 0, 0);
  rotateZ(-0.2);
  if(textureEnabled) texture(texture);
  box(10, 80, 20);
  popMatrix();
  
  // Garis tengah A
  pushMatrix();
  translate(0, 10, 0);
  if(textureEnabled) texture(texture);
  box(25, 8, 15);
  popMatrix();
  
  popMatrix();
}

void drawLetterG(float offsetX, float offsetY) {
  pushMatrix();
  translate(offsetX, offsetY, 0);
  
  if(textureEnabled) {
    fill(255);
  } else {
    fill(100, 255, 100);
  }
  
  // Batang vertikal G
  pushMatrix();
  translate(-15, 0, 0);
  if(textureEnabled) texture(texture);
  box(10, 80, 20);
  popMatrix();
  
  // Garis atas G
  pushMatrix();
  translate(0, -30, 0);
  if(textureEnabled) texture(texture);
  box(35, 10, 20);
  popMatrix();
  
  // Garis bawah G
  pushMatrix();
  translate(0, 30, 0);
  if(textureEnabled) texture(texture);
  box(35, 10, 20);
  popMatrix();
  
  // Garis tengah G
  pushMatrix();
  translate(8, 10, 0);
  if(textureEnabled) texture(texture);
  box(15, 8, 15);
  popMatrix();
  
  popMatrix();
}

void drawLetterU(float offsetX, float offsetY) {
  pushMatrix();
  translate(offsetX, offsetY, 0);
  
  if(textureEnabled) {
    fill(255);
  } else {
    fill(100, 100, 255);
  }
  
  // Batang kiri U
  pushMatrix();
  translate(-15, -10, 0);
  if(textureEnabled) texture(texture);
  box(10, 60, 20);
  popMatrix();
  
  // Batang kanan U
  pushMatrix();
  translate(15, -10, 0);
  if(textureEnabled) texture(texture);
  box(10, 60, 20);
  popMatrix();
  
  // Garis bawah U
  pushMatrix();
  translate(0, 30, 0);
  if(textureEnabled) texture(texture);
  box(35, 10, 20);
  popMatrix();
  
  popMatrix();
}

void drawLightSource() {
  pushMatrix();
  translate(lightX, lightY, lightZ);
  fill(255, 255, 0);
  noFill();
  stroke(255, 255, 0);
  strokeWeight(2);
  sphere(10);
  noStroke();
  popMatrix();
}

void displayControls() {
  // Reset transformasi untuk UI
  camera();
  hint(DISABLE_DEPTH_TEST);
  
  fill(255, 255, 255, 200);
  textAlign(LEFT);
  textSize(12);
  
  text("KONTROL 3D OBJECT AGU:", 10, 20);
  text("Mouse Drag: Rotate (Pitch & Yaw)", 10, 40);
  text("Q/A: Roll Left/Right", 10, 55);
  text("Arrow Keys: Move (Crab/Ped)", 10, 70);
  text("W/S: Zoom In/Out", 10, 85);
  text("T: Toggle Texture", 10, 100);
  text("L: Toggle Lighting/Shadow", 10, 115);
  text("IJKM: Move Light Source", 10, 130);
  text("R: Reset All", 10, 145);
  
  // Status display
  text("Pitch: " + nf(degrees(pitch), 0, 1) + "°", 10, 170);
  text("Yaw: " + nf(degrees(yaw), 0, 1) + "°", 10, 185);
  text("Roll: " + nf(degrees(roll), 0, 1) + "°", 10, 200);
  text("Zoom: " + nf(zoomLevel, 0, 2), 10, 215);
  text("Texture: " + (textureEnabled ? "ON" : "OFF"), 10, 230);
  text("Lighting: " + (shadowEnabled ? "ON" : "OFF"), 10, 245);
  
  hint(ENABLE_DEPTH_TEST);
}

// Kontrol mouse untuk rotasi
void mousePressed() {
  lastMouseX = mouseX;
  lastMouseY = mouseY;
  isDragging = true;
}

void mouseDragged() {
  if(isDragging) {
    float deltaX = mouseX - lastMouseX;
    float deltaY = mouseY - lastMouseY;
    
    yaw += deltaX * 0.01;
    pitch += deltaY * 0.01;
    
    lastMouseX = mouseX;
    lastMouseY = mouseY;
  }
}

void mouseReleased() {
  isDragging = false;
}

// Kontrol keyboard
void keyPressed() {
  switch(key) {
    // Roll control
    case 'q':
    case 'Q':
      roll -= 0.1;
      break;
    case 'a':
    case 'A':
      roll += 0.1;
      break;
    
    // Zoom control
    case 'w':
    case 'W':
      zoomLevel += 0.1;
      break;
    case 's':
    case 'S':
      zoomLevel = max(0.1, zoomLevel - 0.1);
      break;
    
    // Toggle features
    case 't':
    case 'T':
      textureEnabled = !textureEnabled;
      break;
    case 'l':
    case 'L':
      shadowEnabled = !shadowEnabled;
      break;
    
    // Light source control
    case 'i':
    case 'I':
      lightY -= 10;
      break;
    case 'k':
    case 'K':
      lightY += 10;
      break;
    case 'j':
    case 'J':
      lightX -= 10;
      break;
    case 'm':
    case 'M':
      lightX += 10;
      break;
    
    // Reset
    case 'r':
    case 'R':
      pitch = yaw = roll = 0;
      posX = posY = 0;
      zoomLevel = 1.0;
      lightX = 100;
      lightY = -50;
      lightZ = 200;
      break;
  }
  
  // Arrow keys untuk movement (Crab/Ped)
  if(keyCode == UP) {
    posY -= 10;
  } else if(keyCode == DOWN) {
    posY += 10;
  } else if(keyCode == LEFT) {
    posX -= 10;
  } else if(keyCode == RIGHT) {
    posX += 10;
  }
}

// Fungsi untuk export/save (bisa diperluas untuk upload)
void keyTyped() {
  if(key == 'p' || key == 'P') {
    // Save screenshot
    saveFrame("AGU_3D_" + year() + month() + day() + hour() + minute() + second() + ".png");
    println("Screenshot saved!");
  }
}