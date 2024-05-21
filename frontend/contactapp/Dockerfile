# Folosește imaginea de bază Node.js
FROM node:alpine AS build

# Setează directorul de lucru în container
WORKDIR /app

# Copiază fișierele package.json și package-lock.json în container
COPY package*.json ./

# Instalează dependențele
RUN npm install

# Copiază restul fișierelor aplicației în container
COPY . .

# Construiește aplicația React
RUN npm run build

# Folosește o imagine mai mică pentru a reduce dimensiunea finală a containerului
FROM node:alpine

# Instalează serverul 'serve' global
RUN npm install -g serve

# Setează directorul de lucru în container
WORKDIR /app

# Copiază fișierele build din etapa anterioară
COPY --from=build /app/build /app/build

# Expune portul 5000
EXPOSE 5000

# Comandă implicită pentru a porni serverul 'serve'
CMD ["serve", "-s", "build", "-l", "5000"]
