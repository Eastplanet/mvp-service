// config-overrides.js
module.exports = function override(config, env) {
    console.log('Overriding devServer configuration'); // 로그 추가
    config.devServer = {
      ...config.devServer,
      host: 'mvp-project.shop',
      port: 8081,
      allowedHosts: [
        'mvp-project.shop'
      ],
      disableHostCheck: true,
    };
    console.log('-------------------------------------------------');
    console.log('devServer configuration:', config.devServer);
    console.log('-------------------------------------------------');
    return config;
  };
  