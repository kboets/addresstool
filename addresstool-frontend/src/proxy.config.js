var defaultTarget = 'http://localhost:8080';
module.exports = [
  {
    context: ['/addresstool/api/**'],
    target: defaultTarget,
    changeOrigin: true,
  }
];
