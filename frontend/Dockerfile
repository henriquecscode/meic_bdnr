FROM node:14-buster

WORKDIR /usr/src/frontend

COPY ./package.json ./

COPY ./package-lock.json ./

RUN npm install

COPY . .

RUN chown -R node /usr/src/frontend

USER node

CMD ["npm", "start"]