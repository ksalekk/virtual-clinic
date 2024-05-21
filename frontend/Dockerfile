FROM node:18.20-slim AS build

WORKDIR /app
COPY package*.json .
RUN npm install

COPY . .
ARG API_URL
RUN echo "REACT_APP_API_URL=${API_URL}\n" >> .env
RUN cat .env
RUN npm run build


FROM nginx:alpine
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
ENTRYPOINT ["nginx", "-g", "daemon off;"]
