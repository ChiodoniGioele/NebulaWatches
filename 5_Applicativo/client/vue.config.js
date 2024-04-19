const fs = require('fs');

module.exports = {
    configureWebpack: {
      devServer: {
        https: {
          key: fs.readFileSync('./server.key'),
          cert: fs.readFileSync('./server.cert'),
        },
        public: 'https://localhost:8080/',
        headers: { "Access-Control-Allow-Origin": "*" }
      }
    }
};