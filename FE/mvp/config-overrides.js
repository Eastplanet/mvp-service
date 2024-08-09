// config-overrides.js
module.exports = function override(config, env) {
  console.log('Overriding devServer configuration');
  config.devServer = {
    ...config.devServer,
    host: 'localhost',
    port: 8081,
  };
  return config;
};
