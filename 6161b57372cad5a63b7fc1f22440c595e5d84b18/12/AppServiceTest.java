package com.ctrip.apollo.portal;

import com.ctrip.apollo.core.dto.AppDTO;
import com.ctrip.apollo.core.dto.ClusterDTO;
import com.ctrip.apollo.core.enums.Env;
import com.ctrip.apollo.portal.api.AdminServiceAPI;
import com.ctrip.apollo.portal.entity.ClusterNavTree;
import com.ctrip.apollo.portal.service.AppService;
import com.ctrip.apollo.portal.service.ClusterService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AppServiceTest {

  @Mock
  private PortalSettings settings;
  @Mock
  private ClusterService clusterService;
  @Mock
  private AdminServiceAPI.AppAPI appAPI;

  @InjectMocks
  private AppService appService;

  @Test
  public void testBuildNavTree(){
    String appId = "6666";

    ClusterDTO c1 = new ClusterDTO();
    c1.setAppId(appId);
    c1.setName("default");
    c1.setId(1);
    ClusterDTO c2 = new ClusterDTO();
    c2.setAppId(appId);
    c2.setName("oy");
    c2.setId(2);
    List<ClusterDTO> clusterDTOs = Arrays.asList(c1, c2);

    when(settings.getEnvs()).thenReturn(Arrays.asList(Env.DEV, Env.FAT));
    when(clusterService.findClusters(Env.DEV, appId)).thenReturn(clusterDTOs);
    when(clusterService.findClusters(Env.FAT, appId)).thenReturn(Arrays.asList(c1));

    ClusterNavTree tree = appService.buildClusterNavTree(appId);

    assertEquals(2, tree.getNodes().size());
    ClusterNavTree.Node node1 = tree.getNodes().get(0);
    assertEquals(Env.DEV, node1.getEnv());
    assertEquals(2, node1.getClusters().size());
    assertEquals("default", node1.getClusters().get(0).getName());
  }

//  @Test
//  public void testSaveApp(){
//    String appId = "6666";
//    String appName = "hermas";
//    AppDTO appDTO = new AppDTO();
//    appDTO.setAppId(appId);
//    appDTO.setName(appName);
//    appDTO.setDataChangeLastModifiedBy("ll");
//    appDTO.setDataChangeCreatedTime(new Date());
//    appDTO.setOwnerEmail("qq@qq.com");
//    appDTO.setOwnerName("zz");
//
//    when(appService.save(appDTO)).thenReturn(appDTO);
//
//    AppDTO createApp = appService.save(appDTO);
//
//    assertEquals(appId, createApp.getAppId());
//    assertEquals(appName, createApp.getName());
//  }
}
