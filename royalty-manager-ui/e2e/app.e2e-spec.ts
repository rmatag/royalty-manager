import { RoyaltyManagerUiPage } from './app.po';

describe('royalty-manager-ui App', function() {
  let page: RoyaltyManagerUiPage;

  beforeEach(() => {
    page = new RoyaltyManagerUiPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
